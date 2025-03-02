Titanic - Exploratory Data Analysis (EDA)
* This project performs **Exploratory Data Analysis (EDA)** on the Titanic dataset to uncover patterns related to passenger survival. 
* EDA helps us understand the dataset through **statistics, visualizations, and feature engineering** before applying machine learning models.

📊 Dataset Overview
The dataset contains information about **passengers aboard the Titanic**, including their age, gender, passenger class, fare, and survival status.

**Key Features:**
- `Survived` → Target variable (1 = Survived, 0 = Not Survived)
- `Pclass` → Passenger class (1st, 2nd, 3rd)
- `Sex` → Gender of the passenger
- `Age` → Passenger's age
- `SibSp` → Number of siblings/spouses aboard
- `Parch` → Number of parents/children aboard
- `Fare` → Price paid for the ticket
- `Embarked` → Port of Embarkation (C, Q, S)

## 🔍 Steps in Exploratory Data Analysis
1. **Data Loading & Overview**  
   - Understanding dataset structure (`.info()`, `.describe()`)
   - Checking missing values

2. **Data Cleaning & Handling Missing Values**  
   - Filling missing `Age` values with median  
   - Dropping `Cabin` (too many missing values)  

3. **Univariate Analysis (Feature Distributions)**  
   - Visualizing `Age`, `Fare`, and `Survived`  
   - Checking outliers using boxplots  

4. **Bivariate Analysis (Relationships Between Features)**  
   - `Survival Rate by Gender` (Women survived more)  
   - `Survival Rate by Passenger Class` (1st class had highest survival)  

5. **Feature Engineering & Outlier Handling**  
   - Created `FamilySize` (SibSp + Parch + 1)  
   - Applied **Winsorization & Log Transformation** on `Fare`  

6. **Feature Scaling (Normalization & Standardization)**  
   - Used **MinMaxScaler** for Normalization  
   - Used **StandardScaler** for Standardization  

7. **Correlation Analysis & Insights**  
   - Used a **Heatmap** to find relationships between features  
   - Found `Fare` and `Pclass` impact survival chances

8. **Normailzation and Standardization**

## Key Insights from EDA
**Men had a much higher survival rate than Women**  
**3rd Class passengers had the highest survival rate**  
**Passengers with higher fares had better chances of survival**  
**Family size affects survival (very large families had lower survival)**  

