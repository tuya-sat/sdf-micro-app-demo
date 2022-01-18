declare module "*.css";
declare module "*.module.less";
declare module "*.sass";
declare module "*.png";
declare module "*.jpg";
declare module "*.jpeg";
declare module "*.gif";
declare module "*.webp";
declare module "*.svg" {
  export function ReactComponent(
    props: React.SVGProps<SVGSVGElement>
  ): React.ReactElement;
  const url: string;
  export default url;
}
interface Window {
  __POWERED_BY_QIANKUN__: string;
}
