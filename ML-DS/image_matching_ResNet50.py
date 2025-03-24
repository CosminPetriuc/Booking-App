import torch
import torch.nn as nn
from torchvision import models, transforms
from PIL import Image
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

# Function to load and preprocess the image
def preprocess_image(image_path):
    transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
    ])
    img = Image.open(image_path)
    img = transform(img).unsqueeze(0)  # Add batch dimension
    return img

# Function to extract features using ResNet
def extract_features(model, image_tensor):
    with torch.no_grad():  # Disable gradient computation (inference only)
        features = model(image_tensor)
        features = features.view(features.size(0), -1)  # Flatten to 1D vector
    return features.numpy()

# Load the saved dataset features and image names
def load_saved_features():
    image_features = np.load('/Users/andreiv/Documents/Homies/ML-DS/image_features.npy')  # Feature vectors
    image_names = np.genfromtxt('/Users/andreiv/Documents/Homies/ML-DS/image_names.csv', delimiter=',', dtype=str)  # Image names
    return image_features, image_names

# Function to compute cosine similarity and find the top matches
def find_top_matches(new_image_features, dataset_features, top_k=5):
    similarities = cosine_similarity(new_image_features, dataset_features)
    sorted_indices = np.argsort(-similarities[0])  # Sort in descending order
    return sorted_indices[:top_k]

# Load the pretrained ResNet model and remove the classification layer
def load_resnet_model():
    from torchvision.models import ResNet50_Weights
    model = models.resnet50(weights=ResNet50_Weights.IMAGENET1K_V1)
    model = nn.Sequential(*list(model.children())[:-1])  # Remove the last layer
    model.eval()  # Set the model to evaluation mode
    return model

# Main function to match new image
def match_image(new_image_path, top_k=5):
    # Step 1: Load ResNet model
    model = load_resnet_model()

    # Step 2: Preprocess and extract features for the new image
    new_image_tensor = preprocess_image(new_image_path)
    new_image_features = extract_features(model, new_image_tensor)

    # Step 3: Load saved dataset features and image names
    dataset_features, image_names = load_saved_features()

    # Step 4: Compute cosine similarity and find top matches
    top_indices = find_top_matches(new_image_features, dataset_features, top_k)

    # Step 5: Return top matching image names
    top_matches = [image_names[i] for i in top_indices]
    return top_matches

# Example usage
if __name__ == "__main__":
    new_image_path = '/Users/andreiv/Documents/Homies/ML-DS/Data/generated_images_Amsterdam/Your_own_cottage_in_the_countryside_front.png'  # Input the path to the new image
    top_k = 5  # Number of top matches to return
    top_matches = match_image(new_image_path, top_k)
    
    print("Top matches:")
    for i, match in enumerate(top_matches):
        print(f"{i+1}. {match}")
