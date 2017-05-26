import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CallLogComponent } from './call-log.component';
import { CallLogDetailComponent } from './call-log-detail.component';
import { CallLogPopupComponent } from './call-log-dialog.component';
import { CallLogDeletePopupComponent } from './call-log-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CallLogResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const callLogRoute: Routes = [
    {
        path: 'call-log',
        component: CallLogComponent,
        resolve: {
            'pagingParams': CallLogResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'callLoggerApp.callLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'call-log/:id',
        component: CallLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'callLoggerApp.callLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const callLogPopupRoute: Routes = [
    {
        path: 'call-log-new',
        component: CallLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'callLoggerApp.callLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'call-log/:id/edit',
        component: CallLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'callLoggerApp.callLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'call-log/:id/delete',
        component: CallLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'callLoggerApp.callLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
