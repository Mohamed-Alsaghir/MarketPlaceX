<template>
  <div class="col">
      <div class="card" data-bs-toggle="modal" data-bs-target="#staticBackdrop"><!--@click="openProductDetails(productProp);"-->
        <RouterLink class="no-underline" :to="{ name: 'Product Details', params: { id: props.productProp?.product_id } }">
          <img v-if="productProp?.imageURL" id="product_image" :src="productProp?.imageURL" class="card-img-top" alt="...">
          <img v-else id="product_image" src="https://via.placeholder.com/500" class="card-img-top" alt="...">
        </RouterLink>
        <div class="card-body">
          <RouterLink class="no-underline" :to="{ name: 'Product Details', params: { id: props.productProp?.product_id } }">
            <h5 class="card-title">{{ productProp?.name }}</h5>
            <p class="card-text fs-6" style="margin-top: -10px">Release Year: {{ productProp?.yearOfProduction }}  |  Color: {{ productProp?.color }}  |  Condition: {{ productProp?.condition }}  |  Date Added: {{ productProp?.addedDate }}</p>
            <p class="card-text">{{ productProp?.description }}</p>
        </RouterLink>
          <div class="row mx-1">
            <span class="card-text fs-2 fw-semibold col-8">{{ productProp?.price }}</span>
            <span href="#" class="btn btn-primary fs-4 fw-semibold col-4" @click="addToCart(productProp)">Köp</span>
          </div>
        </div>
      </div>
  </div>
</template>

<script setup lang="ts">
import {computed, defineProps, reactive, ref} from 'vue';
import {Product} from "@/model/Product";
import ProductModal from "@/components/ProductModal.vue";
import router from "@/router";
import {useShoppingCartStore} from "@/stores/ShoppingCart";

const props = defineProps({
  productProp: Product,
});

const addToCart = (product: Product) => {
  console.log("Product added to cart", product);
  useShoppingCartStore().addProductToShoppingCart(product);
}


let key = ref(0);

const currentlySelectedProduct = reactive({
  product_id : "",
  type: "",
  price: "",
  yearOfProduction: "",
  color: "",
  condition: "",
  name: "",
  isAvailable: "",
  addedDate: "",
  owner_id: "",
  imageURL: "",
  description: "",
});

const openProductDetails = (product: Product | undefined) : void => {
  //route to ProductDetailsView
  //router.push({ name: 'ProductDetails', params: { id: product?.product_id } });

}

/*const selectThisProduct = (product: Product | undefined) : void => {
  console.log("Product selected", product);
  /*currentlySelectedProduct.name = product?.name ?? "";
  currentlySelectedProduct.product_id = product?.product_id ?? "";
  currentlySelectedProduct.type = product?.type ?? "";
  currentlySelectedProduct.price = product?.price ?? "";
  currentlySelectedProduct.yearOfProduction = product?.yearOfProduction ?? "";
  currentlySelectedProduct.color = product?.color ?? "";
  currentlySelectedProduct.condition = product?.condition ?? "";
  currentlySelectedProduct.isAvailable = product?.isAvailable ?? "";
  currentlySelectedProduct.addedDate = product?.addedDate ?? "";
  currentlySelectedProduct.owner_id = product?.owner_id ?? "";
  currentlySelectedProduct.imageURL = product?.imageURL ?? "";
  currentlySelectedProduct.description = product?.description ?? "";*//*
  // Use Object.assign to ensure reactivity is maintained
  Object.assign(currentlySelectedProduct, {
    name: product?.name ?? "",
    product_id: product?.product_id ?? "",
    type: product?.type ?? "",
    price: product?.price ?? "",
    yearOfProduction: product?.yearOfProduction ?? "",
    color: product?.color ?? "",
    condition: product?.condition ?? "",
    isAvailable: product?.isAvailable ?? "",
    addedDate: product?.addedDate ?? "",
    owner_id: product?.owner_id ?? "",
    imageURL: product?.imageURL ?? "",
    description: product?.description ?? ""
  });
  key.value++;
}*/





/*const selectProduct = (product: Product) => {
  currentlySelectedProduct.value = product;
}*/

/*const getSelectedProductName = () => {
  return currentlySelectedProduct.value?.name;
}*/


const selectedProductName = computed(() => currentlySelectedProduct.name);















//method to check if name and description are empty or undefined
const isProductEmpty = (product: Product) => {
  let output = false;
  const isNameEmpty = !product.name?.trim();
  const isDescriptionEmpty = !product.description?.trim();
  if (product.name.trim() == "" || product.description.trim() == "") {
    output = true;
  }
  if (product.name == undefined || product.description == undefined) {
    output = true;
  }
  /*if (isNameEmpty || isDescriptionEmpty) {
    output = true;
  }*/
  return output;
}








</script>
<style scoped>
.no-underline {
  text-decoration: none;
  color: black;
}
</style>