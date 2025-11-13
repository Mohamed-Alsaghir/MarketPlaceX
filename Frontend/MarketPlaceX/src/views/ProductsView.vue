<template>

  <div class="container">
    <Search class="mt-3" />

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-3 mb-3">
      <!--<ProductCard />
      <ProductCard />-->

      <ProductCard v-for="product in store.products" :key="product.product_id" :productProp="product"/><!--v-if="store.isUserSignedIn() ? '' : 'hidden'"-->



      <!--<Notification :notificationMessageProp="'hahahfkahdfkaf'" />-->

      <!--<div class="col">
        <div class="card">
          <img src="https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?cs=srgb&dl=pexels-souvenirpixels-417074.jpg&fm=jpg" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title placeholder-wave">
              <span class="placeholder col-6"></span>
            </h5>
            <p class="card-text placeholder-wave">
              <span class="placeholder col-12"></span>
              <span class="placeholder col-10"></span>
              <span class="placeholder col-8"></span>
            </p>
            <a class="btn btn-primary disabled placeholder col-6" aria-disabled="true"></a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img src="https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?cs=srgb&dl=pexels-souvenirpixels-417074.jpg&fm=jpg" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title placeholder-wave">
              <span class="placeholder col-6"></span>
            </h5>
            <p class="card-text placeholder-wave">
              <span class="placeholder col-12"></span>
              <span class="placeholder col-10"></span>
              <span class="placeholder col-8"></span>
            </p>
            <a class="btn btn-primary disabled placeholder col-6" aria-disabled="true"></a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img src="https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?cs=srgb&dl=pexels-souvenirpixels-417074.jpg&fm=jpg" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title placeholder-wave">
              <span class="placeholder col-6"></span>
            </h5>
            <p class="card-text placeholder-wave">
              <span class="placeholder col-12"></span>
              <span class="placeholder col-10"></span>
              <span class="placeholder col-8"></span>
            </p>
            <a class="btn btn-primary disabled placeholder col-6" aria-disabled="true"></a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <img src="https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?cs=srgb&dl=pexels-souvenirpixels-417074.jpg&fm=jpg" class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title placeholder-wave">
              <span class="placeholder col-6"></span>
            </h5>
            <p class="card-text placeholder-wave">
              <span class="placeholder col-12"></span>
              <span class="placeholder col-10"></span>
              <span class="placeholder col-8"></span>
            </p>
            <a class="btn btn-primary disabled placeholder col-6" aria-disabled="true"></a>
          </div>
        </div>
      </div>-->
    </div>
  </div>


</template>

<script setup lang="ts">
import Search from "@/components/Search.vue";
import ProductCard from "@/components/ProductCard.vue";

import {onMounted} from "vue";
import { useProductsStore } from '@/stores/products';

const store = useProductsStore();

onMounted(async () => {
  await store.fetchProducts();
});

</script>