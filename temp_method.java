private List<OrderDishResponseModel> OrderDishResponse(List<DishModel> dishes, List<OrderDishBasicModel> orderDishes) {
    return dishes.stream()
            .map(dish -> {
                OrderDishResponseModel dishResponse = new OrderDishResponseModel();
                dishResponse.setName(dish.getName());
                dishResponse.setDescription(dish.getDescription());
                dishResponse.setPrice(dish.getPrice());
                dishResponse.setImageUrl(dish.getImageUrl());

                Integer quantity = orderDishes.stream()
                        .filter(orderDish -> orderDish.getDishId().equals(dish.getId()))
                        .findFirst()
                        .map(OrderDishBasicModel::getQuantity)
                        .orElse(0);
                dishResponse.setQuantity(quantity);

                return dishResponse;
            })
            .toList();
}