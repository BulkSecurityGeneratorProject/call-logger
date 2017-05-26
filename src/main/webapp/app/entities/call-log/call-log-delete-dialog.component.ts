import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CallLog } from './call-log.model';
import { CallLogPopupService } from './call-log-popup.service';
import { CallLogService } from './call-log.service';

@Component({
    selector: 'jhi-call-log-delete-dialog',
    templateUrl: './call-log-delete-dialog.component.html'
})
export class CallLogDeleteDialogComponent {

    callLog: CallLog;

    constructor(
        private callLogService: CallLogService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.callLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'callLogListModification',
                content: 'Deleted an callLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-call-log-delete-popup',
    template: ''
})
export class CallLogDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private callLogPopupService: CallLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.callLogPopupService
                .open(CallLogDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
