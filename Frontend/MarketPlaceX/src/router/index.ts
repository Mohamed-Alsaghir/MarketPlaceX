import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SignIn from '@/views/SignIn.vue'
import SignUp from '@/views/SignUp.vue'
import Products from '@/views/ProductsView.vue'
import MyPage from '@/views/MyPage.vue'
import MyInformation from "@/components/MyInfo.vue";
import MyOrders from "@/components/MyOrders.vue";
import PurchaseRequests from "@/components/PurchaseRequests.vue";
import InterestsList from "@/components/MyInterests.vue";
import ProductDetailsView from "@/views/ProductDetailsView.vue";
import AddProduct from "@/views/AddProduct.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HomeView
    },
    {

      path: '/signin',
      name: 'SignIn',
      component: SignIn
    },
    {
      path: '/signup',
      name: 'Sign Up',
      component: SignUp
    },
    {
        path: '/mypage',
        name: 'MyPage',
        component: MyPage,
        children: [
            {
                path: 'myinfo',
                name: 'My Info',
                component: MyInformation
            },
            {
                path: 'myorders',
                name: 'My Order',
                component: MyOrders
            },
            {
                path: 'purchaseRequests',
                name: 'Purchase Requests',
                component: PurchaseRequests
            },
            {
                path: 'myinterests',
                name: 'My Interests',
                component: InterestsList
            }
        ]
    },
    {
        path: '/products',
        name: 'Products',
        component: Products
    },
  {
      path: '/product/:id',
      name: 'Product Details',
      props: true,
      component: ProductDetailsView
  },
      {
          path: '/addproduct',
          name: 'Add Product',
          component: AddProduct
      },
  ]
})
export default router
