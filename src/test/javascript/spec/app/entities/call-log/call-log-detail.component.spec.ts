import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CallLoggerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CallLogDetailComponent } from '../../../../../../main/webapp/app/entities/call-log/call-log-detail.component';
import { CallLogService } from '../../../../../../main/webapp/app/entities/call-log/call-log.service';
import { CallLog } from '../../../../../../main/webapp/app/entities/call-log/call-log.model';

describe('Component Tests', () => {

    describe('CallLog Management Detail Component', () => {
        let comp: CallLogDetailComponent;
        let fixture: ComponentFixture<CallLogDetailComponent>;
        let service: CallLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CallLoggerTestModule],
                declarations: [CallLogDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CallLogService,
                    EventManager
                ]
            }).overrideComponent(CallLogDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CallLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CallLog('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.callLog).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
