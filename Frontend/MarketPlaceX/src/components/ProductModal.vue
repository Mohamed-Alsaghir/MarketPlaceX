<template>
  <div :key="key" id="staticBackdrop" class="modal" tabindex="-1">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ product?.name }}</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <img :src="product?.imageURL">
          <p>{{ product?.description }}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
  </div>



































  <!-- Button trigger modal -->
  <!--<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
    Launch static backdrop modal
  </button>-->

  <!-- Modal -->
  <!--<div id="staticBackdrop" class="modal modal-dialog modal-dialog-centered modal-dialog-scrollable" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            TESTING 123
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary btn-close" data-bs-dismiss="modal" aria-label="Close">Close</button>
            <button type="button" class="btn btn-primary">Understood</button>
          </div>
        </div>
      </div>
    </div>-->
</template>


<script setup lang="ts">
import {computed, defineProps, reactive, ref} from 'vue';
import {Product} from "@/model/Product";
const props = defineProps({
  key: String,
  product: Object as () => Product,
  //productProp: Product,
  /*key: String,
  name : String,
  description : String,
  imgURL : String,*/
});
/*const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal?.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})*/
</script>