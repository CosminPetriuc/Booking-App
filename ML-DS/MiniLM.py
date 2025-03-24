import pandas as pd
import numpy as np
from transformers import AutoTokenizer, AutoModel
import torch
import faiss
import os

# Paths for data and precomputed files
LISTINGS_CSV_PATH = "Data/listings_Amsterdam.csv"
EMBEDDINGS_PATH = "Data/embeddings_amsterdam.npy"
METADATA_PATH = "Data/listings_metadata.csv"
FAISS_INDEX_PATH = "Data/faiss_index.index"

# Step 1: Load the CSV file
listings_df = pd.read_csv("/Users/andreiv/Documents/Homies/ML-DS/Data/listings_Amsterdam.csv")
print(f"Loaded {len(listings_df)} listings from the CSV file.")

# Step 2: Combine relevant fields into a single description
if 'description' not in listings_df.columns:
    listings_df['description'] = listings_df.apply(
        lambda row: f"{row['name']}, located in {row['neighbourhood']}, "
                    f"offers a {row['room_type']} for ${row['price']} per night.", axis=1
    )
    print("Combined relevant fields into descriptions.")

# Step 3: Load MiniLM model and tokenizer
model_name = "microsoft/MiniLM-L12-H384-uncased"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModel.from_pretrained(model_name)
print("MiniLM model and tokenizer loaded.")


# Function to get embeddings from MiniLM
def get_embedding(text):
    inputs = tokenizer(text, return_tensors="pt", truncation=True, padding=True)
    outputs = model(**inputs)
    embeddings = outputs.last_hidden_state.mean(dim=1)  # Pool the embeddings
    return embeddings.detach().numpy()


# Step 4: Generate or load precomputed embeddings and metadata
if os.path.exists(EMBEDDINGS_PATH) and os.path.exists(FAISS_INDEX_PATH):
    print("Loading precomputed embeddings and FAISS index...")
    embedding_matrix = np.load(EMBEDDINGS_PATH)
    listings_df = pd.read_csv(METADATA_PATH)

# Save embeddings to a .npy file (optional, for reusability)
np.save("/Users/andreiv/Documents/Homies/ML-DS/Data/embeddings_amsterdam.npy", embedding_matrix)
print("Embeddings saved to embeddings_amsterdam.npy.")

# Save metadata to a CSV file (optional, for reference)
listings_df.to_csv("/Users/andreiv/Documents/Homies/ML-DS/Data/listings_metadata.csv", index=False)
print("Metadata saved to listings_metadata.csv.")

    # Save embeddings to a .npy file
    np.save(EMBEDDINGS_PATH, embedding_matrix)
    print(f"Embeddings saved to {EMBEDDINGS_PATH}.")

    # Save metadata to a CSV file
    listings_df.to_csv(METADATA_PATH, index=False)
    print(f"Metadata saved to {METADATA_PATH}.")

    # Initialize FAISS and add embeddings
    dimension = embedding_matrix.shape[1]  # Dimension of MiniLM embeddings, typically 384
    index = faiss.IndexFlatL2(dimension)  # Using L2 distance, switch to IndexFlatIP for cosine similarity
    index.add(embedding_matrix)
    print("FAISS index set up and ready to perform searches.")

    # Save FAISS index
    faiss.write_index(index, FAISS_INDEX_PATH)
    print(f"FAISS index saved to {FAISS_INDEX_PATH}.")


# Step 5: Create a function to find similar listings with similarity scores
def find_similar_listings(user_query, preferences=None, top_k=5):
    if not user_query:
        raise ValueError("User query is required to perform the search.")

    # Enrich user query with preferences
#    if preferences:
 #       preference_keywords = ", ".join(preferences)
 #       user_query += f". Preferences: {preference_keywords}"
 #       print(f"Enriched query: {user_query}")

    print(f"Finding similar listings for query: '{user_query}'")

    # Generate embedding for the enriched query
    query_embedding = get_embedding(user_query)

    # Perform similarity search in FAISS
    distances, indices = index.search(query_embedding, top_k)

    # Calculate similarity scores
    similarity_scores = 1 / (1 + distances)

    # Retrieve top-k matching listing descriptions and similarity scores
    similar_listings = listings_df.iloc[indices[0]].copy()
    similar_listings['similarity_score'] = similarity_scores[0]

    # Sort by similarity score
    similar_listings = similar_listings.sort_values(by="similarity_score", ascending=False)

    result = similar_listings[['name', 'neighbourhood', 'room_type', 'price', 'description', 'similarity_score']].to_dict(orient="records")

    return result

# Example Usage
if __name__ == "__main__":
    print("Welcome to the Amsterdam Airbnb Listing Recommender!")
    user_query = "Cozy apartment in the heart of Amsterdam with canal view"
    user_preferences = ["MUSEUM", "FOOD"]  # Example preferences

    try:
        matching_listings = find_similar_listings(user_query, preferences=user_preferences)
        print("\nTop Matching Listings:")
        print(matching_listings)


    except ValueError as e:
        print(e)
