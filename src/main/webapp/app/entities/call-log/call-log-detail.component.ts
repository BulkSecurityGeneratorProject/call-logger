import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { CallLog } from './call-log.model';
import { CallLogService } from './call-log.service';

@Component({
    selector: 'jhi-call-log-detail',
    templateUrl: './call-log-detail.component.html'
})
export class CallLogDetailComponent implements OnInit, OnDestroy {

    callLog: CallLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private callLogService: CallLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCallLogs();
    }

    load(id) {
        this.callLogService.find(id).subscribe((callLog) => {
            this.callLog = callLog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCallLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'callLogListModification',
            (response) => this.load(this.callLog.id)
        );
    }
}
