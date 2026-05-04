# Binary Model Training Guide (60/20/20 Split)

This guide explains the updated binary brain tumor workflow using a dedicated:

- 60% training set
- 20% validation set
- 20% test set

This is more academically correct than the earlier 80/20 split with manual random evaluation.

## Dataset structure

Your dataset should be arranged like this:

```text
ai-model/
  dataset_split/
    train/
      tumor/
      no_tumor/
    val/
      tumor/
      no_tumor/
    test/
      tumor/
      no_tumor/
```

## What each folder means

- `train`:
  Used to teach the model and update weights.

- `val`:
  Used during training to monitor generalization and detect overfitting.

- `test`:
  Used only after training for final evaluation on unseen images.

## Why this split is better

In the earlier version, training used an 80/20 split and testing was done by random sampling afterward.

In this version:

- training data is fully separated
- validation data is fully separated
- test data is fully separated

That makes the evaluation more reliable and stronger for final-year presentation.

## Install dependencies

```powershell
pip install -r ai-model/requirements.txt
```

## Train the new 60/20/20 model

```powershell
python ai-model/train_model.py --epochs 15 --batch-size 16 --output-model ai-model\my_model_60_20_20.h5
```

## What this command does

- loads images from `dataset_split/train`
- validates on `dataset_split/val`
- trains a binary CNN
- stops early if validation loss stops improving
- saves the best model as `my_model_60_20_20.h5`

## Evaluate on the separate test set

First make sure `MODEL_PATH` inside `evaluate_model.py` points to:

```python
MODEL_PATH = "ai-model/my_model_60_20_20.h5"
```

Then run:

```powershell
python ai-model/evaluate_model.py
```

This evaluates the model only on:

```text
ai-model/dataset_split/test
```

## Files created or used

- `my_model_60_20_20.h5`
  Final trained model for the 60/20/20 setup

- `training_history_binary.json`
  Stores accuracy and loss values during training

- `class_names_binary.json`
  Stores label order for the binary classes

## How to explain this in presentation

You can say:

"I used a dedicated 60/20/20 split, where 60 percent of the data was used for training, 20 percent for validation, and 20 percent for testing. This made the final evaluation more reliable because the model was tested on a separate unseen dataset."

## Important note

If the app should use this model instead of the older one, update `test_model.py` so the default model path points to:

```python
my_model_60_20_20.h5
```
