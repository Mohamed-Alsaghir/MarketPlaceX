export class Interest {
    interestId: string;
    type: string;
    addedDate: string;
    ownerId: string;

    constructor(interestId: string, type: string, addedDate: string, ownerId: string) {
        this.interestId = interestId;
        this.type = type;
        this.addedDate = addedDate;
        this.ownerId = ownerId;
    }
}