<template>
  <div class="container text-center my-5">
    <h1>Välkommen till</h1>
    <h2>Marketplace</h2>
    <form class="mt-4" @submit.prevent="signUp">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" placeholder="Enter username" v-model="username">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Enter password" v-model="password">
      </div>
      <div class="form-group">
        <label for="fullname">Fullname</label>
        <input type="text" class="form-control" id="fullname" placeholder="Enter fullname" v-model="fullname">
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-control" id="email" placeholder="Enter email" v-model="email">
      </div>
      <div class="form-group">
        <label for="address">Address</label>
        <input type="text" class="form-control" id="address" placeholder="Enter address" v-model="address">
      </div>
      <div class="form-group">
        <label for="dob">Date of Birth</label>
        <input type="date" class="form-control" id="dob" placeholder="Enter date of birth" v-model="dob">
      </div>

      <button type="submit" class="btn btn-primary mt-3">Sign Up</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import backendController from "@/Controller/BackendController";
import {ref} from "vue";
import router from "@/router";
//get the username and password from the form
const username = ref("");
const fullname = ref("");
const email = ref("");
const address = ref("");
const dob = ref("");
const password = ref("");

//sign in the user
const signUp = async () => {
        await backendController.signUp(username.value, fullname.value, email.value, address.value, dob.value, password.value);
        router.push({name: "Home"});
}
</script>
