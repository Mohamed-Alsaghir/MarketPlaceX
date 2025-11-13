<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary" style="width: 100vw;">
    <div class="container-fluid">
      <RouterLink class="navbar-brand" to="/">MarketPlaceX</RouterLink>
      <!--<RouterLink to="/signin" class="btn btn-primary d-none d-lg-block">Sign In</RouterLink>-->
      <button class="btn btn-primary d-lg-none ms-auto me-2 position-relative" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight"><i class="bi bi-cart"></i><span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
    {{ cartStore.cartProducts.length }}
  </span></button>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/">Home</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" to="/products">Products</RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" to="/mypage/myinfo">My Page</RouterLink>
          </li>
          <li class="nav-item dropdown">
            <RouterLink class="nav-link dropdown-toggle" to="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Add Interest
            </RouterLink>
            <ul class="dropdown-menu" style="width: 300px;">
              <form class="px-4 py-3" @submit.prevent="addInterest">
                <div class="mb-3">
                  <p>Notify me of Products</p>
                  <input type="text" class="form-control" placeholder="e.g. Computer" v-model="categoryOfInterest">
                </div>
                <div class="text-center">
                  <button type="submit" class="btn btn-primary">Add Interest</button>
                </div>
              </form>
            </ul>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" :to="{ name: 'Add Product' }">Add Product</RouterLink>
          </li>
          <div class="dropdown">
                  <RouterLink class="nav-link dropdown-toggle" to="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                          <i class="bi bi-inbox"></i>
                  </RouterLink>
            <!--<button class="btn btn-primary position-relative" type="button" data-bs-toggle="dropdown" aria-expanded="false" @click="fetchMyInbox(JSON.parse(localStorage.getItem('currentUser')).user_id)">
              <i class="bi bi-inbox"></i>
              <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">-->
                <!--{{ cartStore.cartProducts.length }}--><!--
              </span>
            </button>-->
            <ul class="dropdown-menu">
              <li>Message 1</li>
              <li>Message 2</li>
              <li>Message 3</li>
            </ul>
          </div>
        </ul>
        <button class="btn btn-primary d-none d-lg-block position-relative" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                <i class="bi bi-cart"></i>
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        {{ cartStore.cartProducts.length }}
                </span>
        </button>
      </div>
    </div>
  </nav>


  <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">

    <div class="offcanvas-header">
      <h5 class="offcanvas-title" id="offcanvasRightLabel">Shopping Cart</h5>
      <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>

    <div class="offcanvas-body">
      <CartItem v-for="product in cartStore.cartProducts" :key="product.product_id" :product="product" />
    </div>

    <div class="text-center mb-3">
      <button type="submit" class="btn btn-primary" @click="placeOrder(cartStore.cartProducts)">Place Order</button>
    </div>

  </div>

</template>

<script setup lang="ts">
import CartItem from "@/components/CartItem.vue";
import {ref} from "vue";
import {useShoppingCartStore} from "@/stores/ShoppingCart";
import backendController from "@/Controller/BackendController";
import type {Product} from "@/model/Product";

const cartStore = useShoppingCartStore();

const categoryOfInterest = ref("");

const placeOrder = (products:Product[]) => {
  backendController.placeOrder(products)
  console.log(products);
  alert("Order Placed Successfully");
  cartStore.clearCart();
}

const addInterest = () => {
  if(categoryOfInterest.value === "") return;
  backendController.addInterest(categoryOfInterest.value);
  alert("Interest Added Successfully");
}

const fetchMyInbox = async (userId: number) => {

}
</script>