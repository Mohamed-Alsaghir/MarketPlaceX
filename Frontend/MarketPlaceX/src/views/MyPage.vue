<template>
  <div class="mx-5 mt-5">
    <div class="profile-section d-flex align-items-center mb-4">
      <img src="https://via.placeholder.com/100" class="rounded-circle" alt="Profile Image">
      <h4 class="ms-3s">{{ myInfo.username }}</h4>
    </div>
  </div>
  <div class="row mx-5">
    <div class="col-md-3 d-flex flex-column align-items-center mb-4">
      <div class="list-group w-100">
        <RouterLink :to="{ name: 'My Info' }" class="list-group-item list-group-item-action">My Information</RouterLink>
        <RouterLink :to="{ name: 'My Order' }" class="list-group-item list-group-item-action">My Orders</RouterLink>
        <RouterLink :to="{ name: 'Purchase Requests' }" class="list-group-item list-group-item-action">Purchase Requests</RouterLink>
        <RouterLink :to="{ name: 'My Interests'}" class="list-group-item list-group-item-action">My Interests</RouterLink>
      </div>
    </div>
    <div class="col-md-9">
      <RouterView />
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, type Ref, ref} from 'vue';
import type {User} from "@/model/User";

const myInfo:Ref<User> = ref({});
onMounted(async () => {
  //get info from localStorage
  const user = localStorage.getItem('currentUser');
  myInfo.value = user ? JSON.parse(user) : {};
});
</script>

<style scoped>
.container {
  margin-top: 20px;
}

.profile-section {
  display: flex;
  align-items: center;
  text-align: left;
}

.profile-section img {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.profile-section h4 {
  font-weight: 600;
  margin-left: 15px;
}

.list-group {
  width: 100%;
}

.list-group-item {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  font-weight: 600;
  color: #333;
  text-align: left;
  padding: 10px 15px;
}

.list-group-item-action:hover {
  background-color: #e2e6ea;
  color: #333;
}

.content-placeholder {
  background-color: #e2e6ea;
  border: 1px solid #dee2e6;
  height: 300px;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #000000;
  margin-left: 20px;
}
</style>