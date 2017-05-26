import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { CallLog } from './call-log.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CallLogService {

    private resourceUrl = 'api/call-logs';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(callLog: CallLog): Observable<CallLog> {
        const copy = this.convert(callLog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(callLog: CallLog): Observable<CallLog> {
        const copy = this.convert(callLog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CallLog> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.startTime = this.dateUtils
            .convertDateTimeFromServer(entity.startTime);
        entity.endTime = this.dateUtils
            .convertDateTimeFromServer(entity.endTime);
        entity.callBack = this.dateUtils
            .convertDateTimeFromServer(entity.callBack);
    }

    private convert(callLog: CallLog): CallLog {
        const copy: CallLog = Object.assign({}, callLog);

        copy.startTime = this.dateUtils.toDate(callLog.startTime);

        copy.endTime = this.dateUtils.toDate(callLog.endTime);

        copy.callBack = this.dateUtils.toDate(callLog.callBack);
        return copy;
    }
}
