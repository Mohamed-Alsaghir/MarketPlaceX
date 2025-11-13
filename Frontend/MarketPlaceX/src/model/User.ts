// Product.ts
export class User {
  dateofbirth: string;
  address: string;
  user_id: number;
  isLoggedIn: boolean;
  fullname: string;
  email: string;
  username: string;

  constructor(dateofbirth: string, address: string, user_id: number, isLoggedIn: boolean, fullname: string, email: string, username: string) {
    this.dateofbirth = dateofbirth;
    this.address = address;
    this.user_id = user_id;
    this.isLoggedIn = isLoggedIn;
    this.fullname = fullname;
    this.email = email;
    this.username = username;
  }
}