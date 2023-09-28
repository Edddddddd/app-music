import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArregloDetailComponent } from './arreglo-detail.component';

describe('Arreglo Management Detail Component', () => {
  let comp: ArregloDetailComponent;
  let fixture: ComponentFixture<ArregloDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArregloDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ arreglo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ArregloDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ArregloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load arreglo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.arreglo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
