
export class Order {
    order_id: string;
    order_date: string;
    sold_date: string;
    buyer_id: string;
    seller_id: string;
    status: string;
    product_id: string;

    constructor(order_id: string, product_id: string, buyer_id: string, seller_id: string, order_date: string, sold_date: string, status: string) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.sold_date = sold_date;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.status = status;
        this.product_id = product_id;
    }
}