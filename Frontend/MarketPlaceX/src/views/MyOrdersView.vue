<template>

  <nav class="navbar navbar-light bg-light">
    <button class="btn btn-outline-secondary ml-auto">Add Product</button>
  </nav>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-3 d-flex flex-column align-items-center">
        <div class="profile-section d-flex align-items-center mb-4">
          <img src="https://via.placeholder.com/100" class="rounded-circle" alt="Profile Image">
          <h4 class="ml-3">My orders</h4>
        </div>
        <div class="list-group w-100">
          <a href="#" class="list-group-item list-group-item-action">My Information</a>
          <a href="#" class="list-group-item list-group-item-action">My Orders</a>
          <a href="#" class="list-group-item list-group-item-action">Purchase Requests</a>
          <a href="#" class="list-group-item list-group-item-action">Interests List</a>
        </div>
      </div>
      <div class="col-md-9">
        <div class="content-placeholder_1">
          <p>Current Orders</p>

        </div>
        <div class="rectangle-placeholder"></div> <!-- Nytt element för rektangeln -->
      </div>
      <div class="col-md-9">
        <div class="content-placeholder_2">
          <p>Order History</p>

        </div>
        <div class="rectangle-placeholder"></div> <!-- Nytt element för rektangeln -->
      </div>
    </div>
  </div>

</template>

<script setup lang="ts">

</script>

<style scoped>

</style>