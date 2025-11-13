<template>
  <div class="container mt-5">
    <h1 class="text-center">Add a Product to the MarketPlace</h1>
    <form @submit.prevent="addRequestedProduct">
      <div class="form-group">
        <label for="productName">Name</label>
        <input type="text" class="form-control" id="productName" placeholder="Enter product name" v-model="name">
      </div>
      <div class="form-group">
        <label for="productDescription">Description</label>
        <input type="text" class="form-control" id="productDescription" placeholder="Enter product Description" v-model="description">
      </div>
      <div class="form-group">
        <label for="productCategory">Category</label>
        <input type="text" class="form-control" id="productCategory" placeholder="Enter product category" v-model="category">
      </div>
      <div class="form-group">
        <label for="productPrice">Price</label>
        <input type="number" class="form-control" id="productPrice" placeholder="Enter product price" v-model="price">
      </div>
      <div class="form-group">
        <label for="productYear">Year of Production</label>
        <input type="number" class="form-control" id="productYear" placeholder="Enter year of production" v-model="yearOfProduction">
      </div>
      <div class="form-group">
        <label for="productColor">Color</label>
        <input type="text" class="form-control" id="productColor" placeholder="Enter product color" v-model="color">
      </div>
      <div class="form-group">
        <label for="productCondition">Condition</label>
        <input type="text" class="form-control" id="productCondition" placeholder="Enter product condition" v-model="condition">
      </div>
      <div class="form-group">
        <label for="productCondition">Load Image</label>
        <input type="text" class="form-control" id="productImageURL" placeholder= "Download the image" v-model="imageURL">
      </div>
      <!--<div class="form-group">
        <label for="productImage">Load Image</label>
        <input type="file" class="form-control-file" id="productImage">
      </div>-->
      <button type="submit" class="btn btn-primary">Add</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import backendController from "@/Controller/BackendController";
import {ref} from "vue";
import router from "@/router";
//get the username and password from the form
const name = ref("");
const category = ref("");
const price = ref("");
const yearOfProduction = ref("");
const color = ref("");
const condition = ref("");
const imageURL = ref("");
const description = ref("");


//sign in the user
const addRequestedProduct = async () => {
        await backendController.addRequestedProduct(name.value, category.value, price.value, yearOfProduction.value, color.value, condition.value, imageURL.value, description.value);
        router.push({name: "Products"});
}
</script>