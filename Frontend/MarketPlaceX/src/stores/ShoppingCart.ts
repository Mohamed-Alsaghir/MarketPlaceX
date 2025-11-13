// src/stores/products.js
import { defineStore } from 'pinia';
import { Product } from '@/model/Product';
import {reactive} from "vue";
import {User} from "@/model/User";
export const useShoppingCartStore = defineStore({
    id: 'cartItems',
    state: () => ({
        cartProducts: reactive([] as Product[]),
    }),
    actions: {
        /*async fetchCartItems() {
            return this.cartProducts;
        },*/
        async addProductToShoppingCart(product: Product) {
            //this.cartProducts.push(product);
            this.cartProducts = [...this.cartProducts, product];
            localStorage.setItem('cartProducts', JSON.stringify(this.cartProducts));
        },
        async removeProductFromShoppingCart(product_id: string) {
            this.cartProducts = this.cartProducts.filter(
                product => product.product_id !== product_id
        );
        },
        getCartProducts() {
            return this.cartProducts;
        },

        loadCartProducts() {
            const storedCart = localStorage.getItem('cartProducts');
            if (storedCart) {
                this.cartProducts = JSON.parse(storedCart);
            }
        },

        clearCart() {
            this.cartProducts = [];
            localStorage.removeItem('cartProducts');
        }
    },
});

/*import { defineStore } from 'pinia';
import { Product } from '@/model/Product';
import { reactive, watch } from 'vue';

export const useShoppingCartStore = defineStore({
    id: 'cartItems',
    state: () => ({
        cartProducts: reactive(JSON.parse(localStorage.getItem('cartProducts') || '[]') as Product[]),
    }),
    actions: {
        addProductToShoppingCart(product: Product) {
            this.cartProducts.push(product);
            localStorage.setItem('cartProducts', JSON.stringify(this.cartProducts));
        },
        removeProductFromShoppingCart(product_id: string) {
            this.cartProducts = this.cartProducts.filter(
                product => product.product_id !== product_id
            );
            localStorage.setItem('cartProducts', JSON.stringify(this.cartProducts));
        },
    },
});

// Watch for changes in cartProducts and save to localStorage
watch(() => useShoppingCartStore().cartProducts, (newVal) => {
    localStorage.setItem('cartProducts', JSON.stringify(newVal));
}, { deep: true });*/