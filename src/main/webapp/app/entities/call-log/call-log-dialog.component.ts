import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CallLog } from './call-log.model';
import { CallLogPopupService } from './call-log-popup.service';
import { CallLogService } from './call-log.service';

@Component({
    selector: 'jhi-call-log-dialog',
    templateUrl: './call-log-dialog.component.html'
})
export class CallLogDialogComponent implements OnInit {

    callLog: CallLog;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private callLogService: CallLogService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.callLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.callLogService.update(this.callLog));
        } else {
            this.subscribeToSaveResponse(
                this.callLogService.create(this.callLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<CallLog>) {
        result.subscribe((res: CallLog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CallLog) {
        this.eventManager.broadcast({ name: 'callLogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-call-log-popup',
    template: ''
})
export class CallLogPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private callLogPopupService: CallLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.callLogPopupService
                    .open(CallLogDialogComponent, params['id']);
            } else {
                this.modalRef = this.callLogPopupService
                    .open(CallLogDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
