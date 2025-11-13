// src/stores/products.js
import { defineStore } from 'pinia';
import { Product } from '@/model/Product';
import backendController from "@/Controller/BackendController";
export const useProductsStore = defineStore({
  id: 'products',
  state: () => ({
    products: [] as Product[],
  }),
  actions: {
    async fetchProducts() {
      const response = await backendController.getProducts();
      const data = await response.data;
      console.log(data);
      this.products = data.map(
          (product: any) => new Product(
              product.product_id,
              product.type,
              product.price,
              product.yearOfProduction,
              product.color,
              product.condition,
              product.name,
              product.isAvailable,
              product.addedDate,
              product.owner_id,
              product.imageURL,
              product.description)
      );
    },
    getProductById(id: string) {
      console.log(this.products.find(product => product.product_id === id));
      console.log('ID:', id);
      console.log('Products:', this.products);
      return this.products.find(product => product.product_id === id);
    },
    resetProductsList() {
      this.products = [];
    }
  },
});