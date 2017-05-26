import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CallLog } from './call-log.model';
import { CallLogService } from './call-log.service';
@Injectable()
export class CallLogPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private callLogService: CallLogService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.callLogService.find(id).subscribe((callLog) => {
                callLog.startTime = this.datePipe
                    .transform(callLog.startTime, 'yyyy-MM-ddThh:mm');
                callLog.endTime = this.datePipe
                    .transform(callLog.endTime, 'yyyy-MM-ddThh:mm');
                callLog.callBack = this.datePipe
                    .transform(callLog.callBack, 'yyyy-MM-ddThh:mm');
                this.callLogModalRef(component, callLog);
            });
        } else {
            return this.callLogModalRef(component, new CallLog());
        }
    }

    callLogModalRef(component: Component, callLog: CallLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.callLog = callLog;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
