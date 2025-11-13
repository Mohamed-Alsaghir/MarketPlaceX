<template>

        <div class="content-placeholder row" style="align-items: start; align-content: start; text-align: start; justify-content: start; min-height: 100px; height: 100%;">
                <h2 style="color: #000000">My Interests</h2>
                <div v-for="(myInterest, i) in myInterests" :key="myInterest.interestId" class="card p-2 pt-3 mt-2 mb-2" style="width: 100%">
                        <div class="d-flex justify-content-between align-items-center">
                                <ul class="col-10 col-sm-11">
                                        <li class="fs-4 fw-medium" style="color: #000000">{{ myInterest.type }}</li>
                                </ul>
                                <button class="btn btn-danger col-2 col-sm-1" @click="removeInterest(Number(myInterest.interestId))">
                                        <i class="bi bi-dash-circle-fill"></i>
                                </button>
                        </div>
                </div>
        </div>
</template>

<script setup lang="ts">

import {onMounted, ref, type Ref} from "vue";
import backendController from "@/Controller/BackendController";
import {useProductsStore} from "@/stores/products";
import {Product} from "@/model/Product";
import {Interest} from "@/model/Interest";

const myInterests:Ref<Interest[] | undefined> = ref([]);

const removeInterest = (interestId: number) => {
        backendController.removeInterest(interestId);
        myInterests.value = myInterests.value?.filter((interest) => Number(interest.interestId) !== interestId);
        refreshMyInterests();
}

const refreshMyInterests = async () => {
        const response = await backendController.fetchMyInterests(Number(JSON.parse(localStorage.getItem('currentUser') ?? '{}').user_id));
        const data = response.data;
        myInterests.value = data.map(
            (interest: any) => new Interest(
                interest.interestId,
                interest.type,
                interest.addedDate,
                interest.ownerId)
        );
}

onMounted(async () => {
        refreshMyInterests();
        //const response = await axios.get('/api/purchase-requests');
        //const response = await backendController.fetchMyInterests(Number(JSON.parse(localStorage.getItem('currentUser') ?? '{}').user_id));
        //const response = await backendController.fetchMyOrders(Number(JSON.parse(localStorage.getItem('currentUser') ?? '{}').user_id));

        //const data = await response.data;
        //myOrders.value = response.data;
        //map response to requests
        /*console.log("data");
        console.log(data);
        console.log("data");
        myInterests.value = data.map(
            (interest: any) => //{
                    new Interest(
                        interest.interestId,
                        interest.type,
                        interest.addedDate,
                        interest.ownerId)
                    /*console.log("interest")
                    console.log(interest)
                    console.log("interest")*//*
            //}
        );*/
        /*if (Array.isArray(data)) {
                myInterests.value = data.map(
                    (interest: any) => new Interest(
                        interest.interestId,
                        interest.type,
                        interest.addedDate,
                        interest.ownerId)
                );
        } else {
                console.error('Data is not an array:', data);
        }*/
});
</script>