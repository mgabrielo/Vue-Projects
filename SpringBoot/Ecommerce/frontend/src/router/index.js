import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AddCategory from '../views/Category/AddCategory.vue'
import CategoryView from '../views/Category/CategoryView.vue'
import ProductView from '../views/Product/ProductView.vue'
import AdminView from '../views/AdminView.vue'
import AddProductVue from '@/views/Product/AddProduct.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    redirect: { name: 'CategoryView' }
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory
  },
  {
    path: '/admin/category/view',
    name: 'CategoryView',
    component: CategoryView
  },
  {
    path: '/admin',
    name: 'AdminView',
    component: AdminView
  },
  {
    path: '/admin/product',
    name: 'ProductView',
    component: ProductView
  },
  {
    path: '/admin/product/new',
    name: 'AddProduct',
    component: AddProductVue
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
