
# Pegasos SVM – Python Implementation

This repository contains my Python implementation of the **Pegasos (Primal Estimated sub-GrAdient SOlver for SVM)** algorithm, based on the research paper by Shai Shalev-Shwartz et al. (2007).

The project began with reading and understanding the research paper, particularly the mathematical concepts behind Support Vector Machines, stochastic subgradient descent and optimization. I then worked on implementing the algorithm in Python to understand how its mathematical formulation translates into a working model.

## Research Paper

**Pegasos: Primal Estimated sub-GrAdient SOlver for SVM**

Shai Shalev-Shwartz, Yoram Singer, Nathan Srebro and Koby Crammer (2007).

## Python Implementation

The implementation involves the following steps:

1. Importing the required Python libraries.
2. Loading and exploring the dataset.
3. Preprocessing the data and converting it into numerical features.
4. Splitting the dataset into training and testing sets.
5. Initializing the model parameters and regularization parameter.
6. Implementing the Pegasos update rule using stochastic subgradient descent.
7. Applying regularization and projection during training.
8. Making predictions using the trained model.
9. Evaluating the model using classification metrics.

## Libraries Used

- **NumPy** – Numerical computations and mathematical operations.
- **Pandas** – Data loading and manipulation.
- **Scikit-learn** – Data preprocessing, train-test splitting and evaluation utilities.
- **Matplotlib** – Visualization of experimental results.

## Future Scope and Applications

While exploring machine learning, I became interested in how it could be used to address practical problems, particularly in agriculture.

In 2026, the concerns surrounding El Niño and its possible effects on rainfall and agriculture inspired me to explore an idea involving climate-based agricultural classification.

I initially tried to connect two datasets — historical ENSO data and rainfall data — to explore the relationship between climate patterns and rainfall. Although I couldn't complete the integration, the process gave me an idea of how machine learning could be used to study real-world problems and potentially improve people's lives.

I would like to explore this further by:

- Classifying ENSO conditions into El Niño, La Niña and Neutral phases.
- Studying the relationship between ENSO conditions and regional rainfall patterns.
- Exploring agricultural risk classification using climate and rainfall data.
- Investigating how such predictions could help farmers make more informed decisions about crop selection, sowing periods and water management.

The long-term idea is to explore the development of a climate-based agricultural decision-support system that could make useful climate information more accessible to farmers.

This experience also made me more interested in exploring machine learning beyond its algorithmic and mathematical aspects, particularly its potential to solve problems that affect everyday lives.

## Technologies Used

- Python
- NumPy
- Pandas
- Scikit-learn
- Matplotlib

