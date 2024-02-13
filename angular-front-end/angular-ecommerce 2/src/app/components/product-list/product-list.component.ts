import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../common/product';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {

    products: Product[] = [];
    currentCategoryId: number = 1;
    searchMode: boolean = false;
  
    constructor(private productService: ProductService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
      this.route.paramMap.subscribe(() => {
        this.listProducts();
    });
  }
    listProducts() {

      this.searchMode = this.route.snapshot.paramMap.has('keyword');
      
      if (this.searchMode) {
        this.handleSearchProducts();
      }
      else {
        this.handleListProduct();
      }

      this.handleListProduct();
    }

  handleSearchProducts() {
    const theKeyWord: string = this.route.snapshot.paramMap.get('keyword')!;

    this.productService.searchProducts(theKeyWord).subscribe(
      data => {
        this.products = data;
      }
    );
  }


  handleListProduct() {
    //check if "id" parameter is available
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');

    if(hasCategoryId)
    {
      //get the "id" string, convert it to a number;
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    }
    else
    {
      //default to category 1
      this.currentCategoryId = 1;
    }
    //get products for category ID
    this.productService.getProductList(this.currentCategoryId).subscribe(
      data => {
        this.products = data;
      }
    )
  }
}
