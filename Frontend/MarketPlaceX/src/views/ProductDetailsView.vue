<!--<template>
  <div  class="content">
    <!--<img src="https://www.google.se/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png" class="img-fluid mx-auto d-block" alt="test">
    <p class="fs-1 fw-bolder">TITLE GOES HERE</p>
    <p class="fs-4">DESCRIPTION</p>-->


    <!--<div class="card mx-auto" style="width: 80vw; margin-top: 50px">
      <img src="https://www.google.se/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png" class="img-fluid mx-auto d-block mt-2" alt="test">
      <div class="card-body">
        <h5 class="card-title fs-1 fw-bolder">{{ product?.name }}</h5>
        <p class="card-text">{{ product?.description }}</p>
      </div>
      <div class="card-body d-grid gap-2">
        <a href="#" class="btn btn-primary">Buy</a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type {Product} from "@/model/Product";

const props = defineProps({
  product: Object as () => Product,
});
</script>

<style scoped>

</style>-->

<template>

  <div  class="content">
    <div class="card mx-auto" style="width: 80vw; margin-top: 50px">
      <img :src="chosenProduct?.imageURL" class="img-fluid mx-auto d-block mt-2" alt="test">
      <div class="card-body">
        <h5 class="card-title fs-1 fw-bolder">{{ chosenProduct?.name }}</h5>
        <p class="card-text fs-6" style="margin-top: -10px">Release Year: {{ chosenProduct?.yearOfProduction }}  |  Color: {{ chosenProduct?.color }}  |  Condition: {{ chosenProduct?.condition }}  |  Date Added: {{ chosenProduct?.addedDate }}</p>
        <p class="card-text fs-3">{{ chosenProduct?.description }}</p>
      </div>
      <div class="card-body row">
        <div class="col-6">
          <p class="card-text fs-1 fw-medium" style="width: 100%;">${{ chosenProduct?.price }}</p>
        </div>
        <div class="col-6">
          <a href="#" class="btn btn-primary" style="width: 100%;" @click="addToCart(chosenProduct!)">Buy</a>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup lang="ts">
import BackendController from "@/Controller/BackendController";
import {type Ref, ref} from "vue";
import type {Product} from "@/model/Product";
import {useShoppingCartStore} from "@/stores/ShoppingCart";

const props = defineProps ({
  id: {
    type: String,
    required: true,
  },
})
const chosenProduct: Ref<Product | undefined> = ref<Product>();

BackendController.getProduct(props.id)
    .then((response) => {
  chosenProduct.value = response.data;
}).catch((error) => {
  console.log(error);
});

const addToCart = (product: Product) => {
  console.log("Product added to cart", product);
  useShoppingCartStore().addProductToShoppingCart(product);
}

</script>

<style scoped>

</style>