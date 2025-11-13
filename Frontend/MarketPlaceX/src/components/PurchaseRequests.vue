<template>
        <div class="content-placeholder_1 row" style="min-height: 200px; height: 100%;">
                <h3 style="color: #000000">Purchase Requests</h3>
                <!--<div v-if="isLoading">Loading...</div>-->
                <div v-for="(myPR, i) in myPurchaseRequests" :key="myPR.product_id">
                        <div class="card mt-2 d-flex justify-content-between align-items-center" style="min-height: 200px;">
                                <div class="row g-0">
                                        <div class="col-md-4 d-flex">
                                                <div class="mx-auto">
                                                        <img v-if="productDetails[i].imageURL" :src="productDetails[i].imageURL" class="img-fluid rounded-start" alt="..." style="height: 200px;">
                                                        <img v-else src="https://via.placeholder.com/500" class="img-fluid rounded-start" alt="..." style="height: 200px;">
                                                </div>
                                        </div>
                                        <div class="col-md-8">
                                                <div class="card-body">
                                                        <h5 class="card-title">{{ productDetails[i].name }}</h5>
                                                        <p class="card-text">{{ productDetails[i].description }}</p>
                                                        <p class="card-text text-end fs-3 fw-semibold">{{
                                                                        productDetails[i].price
                                                                }}:-</p>
                                                </div>
                                        </div>
                                        <div v-if="myPR.status === null">
                                        <button class="card-body btn btn-success col-2 align-self-center ms-2 me-1 mb-2 mt-2" style="height: 60px; color: #ffffff;" @click="changePurchaseStatus(Number(myPR.order_id),'accepted')">
                                                <i class="bi bi-check-circle-fill"></i>
                                        </button>
                                        <button class="card-body btn btn-danger col-2 align-self-center ms-1 me-2 mb-2 mt-2" style="height: 60px; color: #ffffff;" @click="changePurchaseStatus(Number(myPR.order_id),'rejected')">
                                                <i class="bi bi-dash-circle-fill"></i>
                                        </button>
                                        </div>
                                        <div v-if="myPR.status === 'accepted'"><!--if='myPR.status === "accepted"'-->
                                                <p class="card-text text-end fs-6 fw-semibold">Accepted</p>
                                                <p class="card-text text-end fs-6 fw-semibold">{{ myPR.sold_date }}</p>
                                        </div>
                                        <div v-if="myPR.status === 'rejected'"><!--if='myPR.status === "accepted"'-->
                                                <p class="card-text text-end fs-6 fw-semibold">Rejected</p>
                                                <p class="card-text text-end fs-6 fw-semibold">{{ myPR.sold_date }}</p>
                                        </div>
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup lang="ts">
import {onMounted, ref, type Ref} from "vue";
import backendController from "@/Controller/BackendController";
import {Order} from "@/model/Order";
import {Product} from "@/model/Product";
import {useProductsStore} from "@/stores/products";

const myPurchaseRequests:Ref<Order[] | undefined> = ref([]);
const productDetails:Ref<Product[]> = ref([]);
//const isLoading = ref(true); // Add this line

/*const purchasesHistory:Ref<Order[] | undefined> = ref([]);
const productDetailsPH:Ref<Product[]> = ref([]);*/

const changePurchaseStatus = async (order_id: number, status: string) => {
        backendController.removeInterest(order_id);
        if (status === 'accepted') {
                backendController.changePurchaseStatus(order_id, status);
                const productInfo = myPurchaseRequests.value?.filter((purchaseRequest) => Number(purchaseRequest.order_id) === order_id)[0];
                backendController.changeAvailabilityAndOwner('false', productInfo?.seller_id ?? 'N/A', productInfo?.product_id ?? 'N/A');
        } else if (status === 'rejected') {
                backendController.changePurchaseStatus(order_id, status);
                const productInfo = myPurchaseRequests.value?.filter((purchaseRequest) => Number(purchaseRequest.order_id) === order_id)[0];
                backendController.changeAvailabilityAndOwner('true', productInfo?.seller_id ?? 'N/A', productInfo?.product_id ?? 'N/A');
        }
        //myPurchaseRequests.value = myPurchaseRequests.value?.filter((purchaseRequest) => Number(purchaseRequest.seller_id) !== purchaseId);
        await refreshMyPurchaseRequests();
}

const refreshMyPurchaseRequests = async () => {
        //isLoading.value = true; // Set loading state to true before async operation
        useProductsStore().resetProductsList();
        await useProductsStore().fetchProducts();
        const response = await backendController.fetchMyPurchaseRequests(Number(JSON.parse(localStorage.getItem('currentUser') ?? '{}').user_id));
        const data = response.data;
        myPurchaseRequests.value = data.map(
                (order: any) => {
                        console.log("order");
                        console.log(order);
                        console.log("order");
                        //if (order.status === null) {
                                /*console.log("order2");
                                console.log(new Order(
                                    order.order_id,
                                    order.product_id,
                                    order.buyer_id,
                                    order.seller_id,
                                    order.order_date,
                                    order.sold_date,
                                    order.status))
                                console.log("order2");*/
                                return new Order(
                                        order.order_id,
                                        order.product_id,
                                        order.buyer_id,
                                        order.seller_id,
                                        order.order_date,
                                        order.sold_date,
                                        order.status);
                        //}
                }
        );
}

onMounted(async () => {
        await refreshMyPurchaseRequests();
        if (useProductsStore().products.length === 0) {
                await useProductsStore().fetchProducts();
        }
        if (myPurchaseRequests.value) {
                productDetails.value = myPurchaseRequests.value.map(pr => useProductsStore().getProductById(pr.product_id)) as Product[];
        }
        /*if (purchasesHistory.value) {
                productDetailsPH.value = purchasesHistory.value.map(ph => useProductsStore().getProductById(ph.product_id))/*.filter(product => product !== undefined)*//* as Product[];
        }*/
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
        color: #6c757d;
        width: 100%;
        margin-bottom: 20px;
}
</style>