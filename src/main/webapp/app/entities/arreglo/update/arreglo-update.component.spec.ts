import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArregloFormService } from './arreglo-form.service';
import { ArregloService } from '../service/arreglo.service';
import { IArreglo } from '../arreglo.model';

import { ArregloUpdateComponent } from './arreglo-update.component';

describe('Arreglo Management Update Component', () => {
  let comp: ArregloUpdateComponent;
  let fixture: ComponentFixture<ArregloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let arregloFormService: ArregloFormService;
  let arregloService: ArregloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ArregloUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ArregloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArregloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    arregloFormService = TestBed.inject(ArregloFormService);
    arregloService = TestBed.inject(ArregloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const arreglo: IArreglo = { id: 456 };

      activatedRoute.data = of({ arreglo });
      comp.ngOnInit();

      expect(comp.arreglo).toEqual(arreglo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArreglo>>();
      const arreglo = { id: 123 };
      jest.spyOn(arregloFormService, 'getArreglo').mockReturnValue(arreglo);
      jest.spyOn(arregloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arreglo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arreglo }));
      saveSubject.complete();

      // THEN
      expect(arregloFormService.getArreglo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(arregloService.update).toHaveBeenCalledWith(expect.objectContaining(arreglo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArreglo>>();
      const arreglo = { id: 123 };
      jest.spyOn(arregloFormService, 'getArreglo').mockReturnValue({ id: null });
      jest.spyOn(arregloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arreglo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arreglo }));
      saveSubject.complete();

      // THEN
      expect(arregloFormService.getArreglo).toHaveBeenCalled();
      expect(arregloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IArreglo>>();
      const arreglo = { id: 123 };
      jest.spyOn(arregloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arreglo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(arregloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
