# NoSQL-based Music Recommendation System
The relationship between musical preferences and individuals' personalities remained a long-running topic of contention for researchers due to the variability of results.
<br>
Starting from an artificial intelligence algorithm used to identify users with similar personalities, we have created a social network for the recommendation of music tracks. In particular, an intelligent algorithm will recommend the most suitable music for the user starting from the favorite songs of users with similar personalities.
<br>
The algorithm created can also evolve, adapting the recommendations according to the consistency expressed by the previous recommendations.<br>
<img width="451" alt="image" src="https://github.com/terranovaa/MusicRecommendationSystem/assets/61695945/647abd4a-70ca-41c1-a0bc-7cc2aed3ebd0">
<br>
This social network relies on a **NoSQL** large-scale and multi-structured database, relying on a Neo4J Graph DB and a MongoDB Document DB.

## Architecture
The software architecture of the application is described in this diagram.<br>
<p align="center">
  <img width="726" alt="image" src="https://github.com/terranovaa/MusicRecommendationSystem/assets/61695945/17ab3381-3d59-4e78-9e79-ac9140d8a7cb">
</p>

## Project Structure
The project is organized as follows:
- backend/ contains the source code of the backend module
- frontend/ contains the source code of the frontend module<br>
<br>
More information about the project can be found in the documentation and presentation.<br>
Executables and the database dumps: https://drive.google.com/drive/folders/1CRJmFdyDWe8Nmyb4AEFrhrufuZyEI58V?usp=sharing

## Clustering techniques for grouping personalities
The AI study using clustering techniques to group users with the same personalities can be found at: https://github.com/terranovaa/PersonalityClusteringAnalysis

