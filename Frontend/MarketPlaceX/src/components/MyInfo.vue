<template>
        <div class="content-placeholder_1 row" style="min-height: 200px">
                <h3>My Information</h3>
                <p>Name: {{ myInfo?.fullname }}</p>
                <p>Username: {{ myInfo?.username }}</p>
                <p>Email: {{ myInfo?.email }}</p>
                <p>Address: {{ myInfo?.address }}</p>
                <p>Date Of Birth: {{ myInfo?.dateofbirth }}</p>
        </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import {ref, onMounted, type Ref} from 'vue';
import type {User} from "@/model/User";

const myInfo:Ref<User | undefined> = ref();
onMounted(async () => {
  //get info from localStorage
  const user = localStorage.getItem('currentUser');
  myInfo.value = user ? JSON.parse(user) : {};
});
</script>

<style scoped>

.content-placeholder_1 {
        position: relative;
        background-color: #e2e6ea;
        border: 1px solid #dee2e6;
        padding: 20px;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #000000;
        width: 100%;
        margin-bottom: 20px;
}

</style>