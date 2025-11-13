<template>
        <div class="content-placeholder_1 row" style="min-height: 200px">
                <h3 style="color: #000000">Orders awaiting Approval</h3>
                <!--<ul>
                <li v-for="(myOrder, i) in myOrders" :key="myOrder.product_id">{{ productDetails[i].name }} - {{ productDetails[i].type }}</li>
                </ul>-->
                <div v-for="(myOrder, i) in myOrders" :key="myOrder.product_id">
                        <div v-if="myOrder.sold_date === null" class="card mt-2" style="min-height: 200px;">
                                <div class="row g-0">
                                        <div class="col-md-4 d-flex align-items-center">
                                                <div class="mx-auto">
                                                        <img v-if="productDetails[i].imageURL" :src="productDetails[i].imageURL" class="img-fluid rounded-start" alt="..." style="height: 200px;">
                                                        <img v-else src="https://via.placeholder.com/500" class="img-fluid rounded-start" alt="..." style="height: 200px;">
                                                </div>
                                        </div>
                                        <div class="col-md-8">
                                                <div class="card-body">
                                                        <h5 class="card-title">{{ productDetails[i].name }}</h5>
                                                        <p class="card-text">{{ productDetails[i].description }}</p>
                                                        <p class="card-text text-end fs-3 fw-semibold">{{ productDetails[i].price }}:-</p>
                                                </div>
                                        </div>
                                </div>
                        </div>
                </div>
        </div>
  <div class="content-placeholder_1 row" style="min-height: 200px">
          <h3 style="color: #000000">Order History</h3>
          <div v-for="(myOrder, i) in myOrders" :key="myOrder.product_id">
                  <div v-if="myOrder.sold_date !== null" class="card mt-2" style="height: 200px;">
                          <div class="row g-0">
                                  <div class="col-md-4 d-flex align-items-center">
                                          <div class="mx-auto">
                                                  <img :src="productDetails[i].imageURL" class="img-fluid rounded-start" alt="..." style="height: 200px;">
                                          </div>
                                  </div>
                                  <div class="col-md-8">
                                          <div class="card-body">
                                                  <h5 class="card-title">{{ productDetails[i].name }}</h5>
                                                  <p class="card-text">{{ productDetails[i].description }}</p>
                                                  <p class="card-text text-end fs-3 fw-semibold">{{ productDetails[i].price }}:-</p>
                                          </div>
                                  </div>
                          </div>
                  </div>
          </div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted, type Ref, computed, watch} from 'vue';
import backendController from "@/Controller/BackendController";
import {Order} from "@/model/Order";
import {useProductsStore} from "@/stores/products";
import {Product} from "@/model/Product";
import {useShoppingCartStore} from "@/stores/ShoppingCart";

const myOrders:Ref<Order[] | undefined> = ref([]);
//const productDetails/*:Ref<Product[]>*/ = ref<Product[]>([]);
const productDetails:Ref<Product[]> = ref([]);
//do a computed property where the Product details are gathered from the product_id in the order from the useProductStore, do this for each order in myOrders
/*if (myOrders.value !== undefined) {
        for (let i = 0; i < myOrders.value.length; i++) {
                //productDetails.value[i] = useProductsStore().getProductById(myOrders.value[i].product_id);
                let product = useProductsStore().getProductById(myOrders.value[i].product_id);
                if (product) {
                        productDetails.value[i] = product;
                }
  }*/
/*watch(myOrders, (newOrders) => {
        if (newOrders) {
                /*productDetails.value = newOrders.map(order => {
                        let product = useProductsStore().getProductById(order.product_id);
                        return product ? product : new Product('', '', '', '', '', '', '', '', '', '', '', '');
                });*//*
                productDetails.value = newOrders.map(order => useProductsStore().getProductById(order.product_id)).filter(Boolean);

        }
}, { immediate: true });*/

//const productDetails = computed(() => useProductsStore().getProductById(myOrders?.value.product_id));
onMounted(async () => {
  //const response = await axios.get('/api/purchase-requests');
  const response = await backendController.fetchMyOrders(Number(JSON.parse(localStorage.getItem('currentUser') ?? '{}').user_id));
  const data = await response.data;
  //myOrders.value = response.data;
  //map response to requests
  myOrders.value = data.map(
      (order: any) => new Order(
          order.order_id,
          order.product_id,
          order.buyer_id,
          order.seller_id,
          order.order_date,
          order.sold_date,
          order.status)
  );/*.then((order: any) => {
          //if (myOrders.value !== undefined) {
                  //for (let i = 0; i < myOrders?.value.length; i++) {
                          //productDetails.value[i] = useProductsStore().getProductById(myOrders.value[i].product_id);
                          //let product = useProductsStore().getProductById(myOrders?.value[i].product_id);
                          let product = useProductsStore().getProductById(order.product_id);
                          //if (product) {
                                  //productDetails.value[i] = product;
                                  productDetails.value.push(product);
                          //}
                  //}
          //}
  });*/
        /*if (myOrders.value !== undefined) {
                for (let i = 0; i < myOrders.value.length; i++) {
                        //productDetails.value[i] = useProductsStore().getProductById(myOrders.value[i].product_id);
                        let product = useProductsStore().getProductById(myOrders.value[i].product_id);
                        if (product) {
                                productDetails.value[i] = product;
                        }
                }
        }*/
        if (useProductsStore().products.length === 0) {
                await useProductsStore().fetchProducts();
        }
        if (myOrders.value) {
                productDetails.value = myOrders.value.map(order => useProductsStore().getProductById(order.product_id))/*.filter(product => product !== undefined)*/ as Product[];
        }
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;400;600&display=swap');

body {
  font-family: 'Poppins', sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.navbar .btn {
  background-color: #f8f9fa;
  border: 1px solid #ccc;
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