import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CallLoggerSharedModule } from '../../shared';
import {
    CallLogService,
    CallLogPopupService,
    CallLogComponent,
    CallLogDetailComponent,
    CallLogDialogComponent,
    CallLogPopupComponent,
    CallLogDeletePopupComponent,
    CallLogDeleteDialogComponent,
    callLogRoute,
    callLogPopupRoute,
    CallLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...callLogRoute,
    ...callLogPopupRoute,
];

@NgModule({
    imports: [
        CallLoggerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CallLogComponent,
        CallLogDetailComponent,
        CallLogDialogComponent,
        CallLogDeleteDialogComponent,
        CallLogPopupComponent,
        CallLogDeletePopupComponent,
    ],
    entryComponents: [
        CallLogComponent,
        CallLogDialogComponent,
        CallLogPopupComponent,
        CallLogDeleteDialogComponent,
        CallLogDeletePopupComponent,
    ],
    providers: [
        CallLogService,
        CallLogPopupService,
        CallLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CallLoggerCallLogModule {}
