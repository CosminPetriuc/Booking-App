import torch
import torch.nn as nn
from torchvision import models, transforms
from PIL import Image
import os
import numpy as np

# Load the pretrained ResNet model
model = models.resnet50(pretrained=True)

# Remove the last classification layer to get feature representations
model = nn.Sequential(*list(model.children())[:-1])

# Set model to evaluation mode (important for inference)
model.eval()

# Image preprocessing pipeline: resize, convert to tensor, and normalize
transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

# Function to preprocess and load a single image
def preprocess_image(image_path):
    img = Image.open(image_path)
    img = transform(img).unsqueeze(0)  # Add batch dimension
    return img

# Function to extract features using ResNet
def extract_features(model, image_tensor):
    with torch.no_grad():  # Disable gradient computation (inference only)
        features = model(image_tensor)
        features = features.view(features.size(0), -1)  # Flatten to 1D vector
    return features.numpy()

# Directory containing your dataset images
dataset_dir = '/Users/andreiv/Documents/Homies/ML-DS/Data/generated_images_Amsterdam'

# Create an empty list to store image features and corresponding image names
image_features = []
image_names = []

# Loop over all images in your dataset directory
for img_file in os.listdir(dataset_dir):
    # Ensure the file is an image (you can add more extensions if needed)
    if img_file.endswith(('.jpg', '.jpeg', '.png')):
        image_path = os.path.join(dataset_dir, img_file)
        
        # Preprocess the image and extract features
        img_tensor = preprocess_image(image_path)
        features = extract_features(model, img_tensor)
        
        # Append the features and image name to the lists
        image_features.append(features)
        image_names.append(img_file)

# Convert the list of image features into a NumPy array (for saving to a file)
image_features = np.vstack(image_features)  # Stack into 2D array

# Save the image features and image names for later use
np.save('image_features.npy', image_features)  # Save the features as .npy file
np.savetxt('image_names.csv', np.array(image_names), delimiter=',', fmt='%s')  # Save the image names as CSV

print("Feature extraction complete! Features saved to 'image_features.npy' and 'image_names.csv'.")
