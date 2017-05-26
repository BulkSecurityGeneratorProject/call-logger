
const enum Direction {
    'INCOMING',
    ' OUTGOING'

};
export class CallLog {
    constructor(
        public id?: string,
        public phoneNumber?: string,
        public firstName?: string,
        public lastName?: string,
        public transcript?: string,
        public recording?: string,
        public direction?: Direction,
        public notes?: string,
        public startTime?: any,
        public endTime?: any,
        public callBack?: any,
    ) {
    }
}
