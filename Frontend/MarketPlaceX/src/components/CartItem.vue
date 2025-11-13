<template>
  <div class="card" style="max-width: 540px;"><!--v-for="item in items" :key="item.product_id"-->
    <div class="row g-0">
      <div class="col-md-4 d-flex align-items-center">
        <div class="mx-auto">
          <img :src="product.imageURL" class="img-fluid rounded-start" alt="...">
        </div>
      </div>
      <div class="col-md-8">
        <div class="card-body">
          <h5 class="card-title">{{ product.name }}</h5>
          <p class="card-text">{{ product.description }}</p>
          <p class="card-text text-end fs-3 fw-semibold">{{ product.price }}:-</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {Product} from "@/model/Product";

//const store = useShoppingCartStore();
//const items = computed(() => store.cartProducts);
//const items = store.getCartProducts();
/*const cartItems = computed(() =>
    store.cartProducts.map((item) => ({
            ...item,
            product: productsStore.products.find((product) => product.id === item.id),
    }))
);*/


/*const items = ref(store.cartProducts);

onMounted(() => {
        store.loadCartProducts();
});*/

const props = defineProps ({
        product: {
                type: Product,
                required: true,
        },
})

</script>