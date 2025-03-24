from flask import Flask, request, jsonify
from image_matching_ResNet50 import match_image
from MiniLM import find_similar_listings

app = Flask(__name__)

@app.route('/process_image', methods=['POST'])
def process_image():
    try:
        # Parse request data
        data = request.json
        if not data:
            return jsonify({"error": "No input data provided"}), 400
        
        image_path = data.get("image_path")
        input_text = data.get("input_text")
        sample_count = data.get("sample_count", 5)  # Default to 5 if not provided
        
        # Validate required parameters
        if not image_path or not input_text:
            return jsonify({"error": "Missing 'image_path' or 'input_text'"}), 400
        
        # Call the functions to process the data
        sample_images = match_image(image_path, sample_count)
        sample_text = find_similar_listings(input_text, sample_count)
        
        # Return the results as a JSON response
        return jsonify({
            "sample_images": sample_images,
            "sample_text": sample_text
        })
    except Exception as e:
        # Handle unexpected errors
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=5000, debug=True)
