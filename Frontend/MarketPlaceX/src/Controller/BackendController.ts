import axios from "axios";
import type {Ref, UnwrapRef} from "vue";
import type {Product} from "@/model/Product";
import {ref} from "vue";

const apiClient = axios.create({
    baseURL: 'http://10.2.1.234:8080',
    withCredentials: false,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
    },
})
const productsToBeOrdered: Ref<{product_id: number, buyer_id: number, seller_id: number}[]> = ref([]);
export default {
    getProducts() {
        return apiClient.get('/api/product/viewAll/')
    },
    //TODO:GetUserInformation
    getProduct(id: string) {
        return apiClient.get('/api/product/view/' + id + "/")
    },
    signIn(username: UnwrapRef<string>, password: UnwrapRef<string>) {
        console.log(username + " & " + password);
        return apiClient.post('/api/user/signin/', {
            username: username,
            password: password
        })
    },
    signUp(username: UnwrapRef<string>, fullName: UnwrapRef<string>, email: UnwrapRef<string>, address: UnwrapRef<string>, dateOfBirth: UnwrapRef<string>, password: UnwrapRef<string>) {
        console.log(username, fullName, email, address, dateOfBirth, password);
        return apiClient.post('/api/user/signup/', {
            username: username,
            fullname: fullName,
            email: email,
            address: address,
            dateofbirth: dateOfBirth,
            password: password
        })
    },
    
    
    placeOrder(products: Product[]) {
        //foreach product in products, add to productsToBeOrdered
        products.forEach(product => {
            productsToBeOrdered.value.push({
                product_id: Number(product.product_id),
                buyer_id: Number(JSON.parse(localStorage.getItem('currentUser')?? '{}').user_id),
                seller_id: Number(product.owner_id)
            });
        });
        console.log(productsToBeOrdered.value.toString());
        return apiClient.post('/api/order/placeOrder/', productsToBeOrdered.value)
    },
    addRequestedProduct(name: UnwrapRef<string>, category: UnwrapRef<string>, price: UnwrapRef<string>, yearOfProduction: UnwrapRef<string>, color: UnwrapRef<string>, condition: UnwrapRef<string>, imageURL: UnwrapRef<string>, description: UnwrapRef<string>) {
        return apiClient.post('/api/product/add/', {
            name: name,
            type: category,
            price: price,
            yearOfProduction: yearOfProduction,
            color: color,
            condition: condition,
            imageURL: imageURL,
            description: description,
            owner_id: '1',
            addedDate: new Date().toISOString().split('T')[0],
            isAvaliable: true
        })
    },
    addInterest(category: UnwrapRef<string>) {
        return apiClient.post('/api/interest/add/', {
            type: category,
            addedDate: new Date().toISOString().split('T')[0],
            owner_id: Number(JSON.parse(localStorage.getItem('currentUser')?? '{}').user_id)
        })
    },
    searchProductsBasedOnCriteria(category: Ref<UnwrapRef<string | null>>, minPrice: Ref<UnwrapRef<number | null>>, maxPrice: Ref<UnwrapRef<number | null>>, condition: Ref<UnwrapRef<string | null>>) {
        return apiClient.get('/api/product/search/' + category.value + '/' + minPrice.value + '/' + maxPrice.value +'/' + condition.value + '/')
    },
    fetchMyOrders(currentUserId: number) {
        return apiClient.get('/api/order/getMyOrders/' + currentUserId + '/')
    },
    fetchMyInterests(ownerId: number) {
        return apiClient.get('/api/interest/viewMyInterests/' + ownerId + '/')
    },
    removeInterest(interestId: number) {
        return apiClient.delete('/api/interest/remove/' + interestId + '/')
    },
    fetchMyPurchaseRequests(number: number) {
        return apiClient.get('/api/order/getMyPurchaseRequests/' + number + '/')
    },
    changePurchaseStatus(order_id: number, status: string) {
        return apiClient.post('/api/order/setStatus/', {
            order_id: order_id,
            status: status
            })
    },
    changeAvailabilityAndOwner(availability: string, seller_id: string, product_id: string) {
        return apiClient.post('/api/product/changeAvailabilityAndOwner/', {
            isAvailable: availability,
            owner_id: seller_id,
            product_id: product_id
        })
    }
}