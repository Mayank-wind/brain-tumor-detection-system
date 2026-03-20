from flask import Flask, request, jsonify
from PIL import Image
import numpy as np

app = Flask(__name__)

# Dummy prediction (will replace later with real model)
def predict_image(img):
    return {
        "result": "Tumor Detected",
        "confidence": 0.95,
        "tumorType": "Glioma"
    }

@app.route('/predict', methods=['POST'])
def predict():
    file = request.files['file']

    img = Image.open(file)
    img = img.resize((224, 224))
    img = np.array(img)

    prediction = predict_image(img)

    return jsonify(prediction)

if __name__ == '__main__':
    app.run(port=5000, debug=True)