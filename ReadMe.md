# E-Commerce Product Management System

A Java-based product management system that parses product data from CSV files and stores them in a **Left-Leaning Red-Black Tree (LLRB)** for fast and balanced lookup.

---

## 🚀 Features

### 🔹 Custom CSV Parser
- Handles quoted fields  
- Supports escaped quotes (`"" → "`)  
- Reads pipe-separated categories  
- Produces clean `Product` objects

### 🔹 Left-Leaning Red-Black Tree
- Balanced binary search tree  
- O(log n) insert & search time  
- Implements rotations and color flips  
- Stores products by product-id

### 🔹 Product Management
- Insert products into the tree  
- Search products by ID  
- Print tree statistics (size, height, red links)

---