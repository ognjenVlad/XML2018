export interface Token {
  sub: string;
  created: Date;
  roles: Array<string>;
  id: number;
  exp: Date;
}
