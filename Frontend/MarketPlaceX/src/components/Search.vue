<template>
  <!--<div class="input-group mb-3">
    <input type="text" class="form-control" placeholder="Sök efter produkter" aria-label="Sök efter produkter" aria-describedby="button-addon2">
    <button class="btn btn-outline-secondary" type="button" id="button-addon2"><i class="bi bi-search"></i></button>
  </div>-->
  <div class="search-container text-center">
    <h2>Search for amazing Products</h2>
    <form class="search-form row" @submit.prevent="search">
      <div class="col">
        <!--<select class="form-control" id="category" v-model="category">
          <option selected disabled>Category</option>
          <option value="Electronics">Electronics</option>
          <option value="Fashion">Fashion</option>
          <option value="Home">Home</option>
          <option value="Toys">Toys</option>
        </select>-->
              <input type="text" class="form-control" placeholder="Category" v-model="category">
      </div>
      <div class="col">
        <input type="number" class="form-control" placeholder="Min Price" v-model="minPrice">
      </div>
      <div class="col">
        <input type="number" class="form-control" placeholder="Max Price" v-model="maxPrice">
      </div>
      <div class="col">
        <!--<select class="form-control" id="condition" v-model="condition">
          <option selected disabled>Condition</option>
          <option value="New">New</option>
          <option value="Used">Used</option>
          <option value="Refurbished">Refurbished</option>
        </select>-->
        <input type="text" class="form-control" placeholder="Condition" v-model="condition">
      </div>
      <div class="col">
        <button type="submit" class="btn btn-primary">Search</button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
//get value from select
import {ref} from "vue";
import backendController from "@/Controller/BackendController";
import {useProductsStore} from "@/stores/products";

const category = ref<string | null>(null);
const minPrice = ref<number | null>(null);
const maxPrice = ref<number | null>(null);
const condition = ref<string | null>(null);
const search = async () => {
        useProductsStore().resetProductsList();
        const response = await backendController.searchProductsBasedOnCriteria(category, minPrice, maxPrice, condition);
        useProductsStore().products = response.data;
}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;400;600&display=swap');

.search-container {
  background-color: #f1f1f1;
  border-radius: 15px;
  padding: 20px;
  margin: 20px auto;
  max-width: 1000px;
}

.search-container h2 {
  font-weight: 600;
  margin-bottom: 20px;
}

.search-form {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: nowrap;
}

.search-form select,
.search-form input,
.search-form button {
  flex: 1;
  margin: 5px;
  padding: 7px;
  font-weight: 600;
  border-radius: 5px;
  border: 1px solid #ccc;
  max-width: 150px; /* Adjust this value as needed */
  box-sizing: border-box;
}

.search-form select {
  background-color: #fff;
  color: #333;
}

.search-form input::placeholder {
  color: #333;
}

.search-form button {
  background-color: #007bff;
  color: white;
  max-width: 150px; /* Adjust this value as needed */
}

.search-form button:hover {
  background-color: #0056b3;
  color: white;
}
</style>