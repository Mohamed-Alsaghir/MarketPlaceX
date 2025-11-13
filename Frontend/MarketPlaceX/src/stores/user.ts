// src/stores/products.js
import { defineStore } from 'pinia';
import {User} from "@/model/User";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        currentUser: new User('', '', 0,false,'','','')
    }),
    actions: {
        getUserInfo() {
            return this.currentUser;
        },
        updateCurrentUser(user: User) {
            this.currentUser = user;
            localStorage.setItem('currentUser', JSON.stringify(user));
        },
        loadCurrentUser() {
            const storedUser = localStorage.getItem('currentUser');
            if (storedUser) {
                this.currentUser = JSON.parse(storedUser);
            }
        }
    },
});