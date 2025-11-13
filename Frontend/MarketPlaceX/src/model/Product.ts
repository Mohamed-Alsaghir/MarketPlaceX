// Product.ts
export class Product {
    product_id : string;
    type : string;
    price : string;
    yearOfProduction : string;
    color : string;
    condition : string;
    name : string;
    isAvailable : string;
    addedDate : string;
    owner_id : string;
    imageURL : string;
    description : string;

    constructor(product_id : string, type : string, price : string, yearOfProduction : string, color : string, condition : string, name : string, isAvailable : string, addedDate : string, owner_id : string, imageURL : string, description : string) {
        this.product_id = product_id
        this.type = type
        this.price = price
        this.yearOfProduction = yearOfProduction
        this.color = color
        this.condition = condition
        this.name = name
        this.isAvailable = isAvailable
        this.addedDate = addedDate
        this.owner_id = owner_id
        this.imageURL = imageURL
        this.description = description
    }
}