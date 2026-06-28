from django.urls import path
from . import views

urlpatterns = [
    # Cambiamos 'admin' por 'gestion'
    path('gestion/posts/', views.admin_posts_list, name='admin_posts_list'),
    path('gestion/posts/delete/<int:pk>/', views.delete_post, name='delete_post'),
    path('gestion/posts/excel/', views.export_posts_excel, name='export_posts_excel'),
    path('gestion/posts/pdf/', views.export_posts_pdf, name='export_posts_pdf'),
    path('gestion/posts/edit/<int:pk>/', views.edit_post, name='edit_post'),
]