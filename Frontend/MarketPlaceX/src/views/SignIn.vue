<template>
  <div class="container text-center my-5">
    <h1>Välkommen till</h1>
    <h2>Marketplace</h2>
    <form class="mt-4" @submit.prevent="signIn">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" placeholder="Enter username" v-model="username">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Enter password" v-model="password">
      </div>
      <button type="submit" class="btn btn-primary mt-3">Sign In</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import backendController from "@/Controller/BackendController";
import {ref} from "vue";
import {useUserStore} from "@/stores/user";
import router from "@/router";
import {Product} from "@/model/Product";
//get the username and password from the form
const username = ref("");
const password = ref("");
//sign in the user
const signIn = async () => {
  //save the response from the backend as a variable
  const loggedInUser =  await backendController.signIn(username.value, password.value)
  const data = loggedInUser.data;
  useUserStore().updateCurrentUser(data);

  /*const response = await backendController.getProducts();
  const data = await response.data;
  this.products = data.map(
      (product: any) => new Product(product.product_id, product.type, product.price, product.yearOfProduction, product.color, product.condition, product.name, product.isAvailable, product.addedDate, product.owner_id, product.imageURL, product.description)
  );*/

  //if the response is successful, redirect to the home page
  /*if (loggedInUser === undefined ) {
    return;
  }*/
  router.push({name: "Products"});
}
</script>