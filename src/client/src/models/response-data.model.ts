export class ResponseData<T> {
    public data: T[] = [];
    public page: PageInfo;
    public links: Link[] = [];

    constructor(page: PageInfo) {
        this.page = page;
    }
}

export class PageInfo {
    public number: number;
    public size: number;
    public totalElements: number;
    public totalPages: number;

    constructor(number: number, size: number, totalElements: number, totalPages: number) {
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}

export class Link {
    public rel: string;
    public href: string;

    constructor(rel: string, href: string) {
        this.rel = rel;
        this.href = href;
    }
}