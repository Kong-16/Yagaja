export function deepCopy<T>(obj: T): T | undefined {  // eslint-disable-line
  if(typeof obj !== 'object' || obj === null) {
      return obj;
  }

  if(obj instanceof Date) {
      return new Date(obj.getTime()) as any;  // eslint-disable-line
  }

  if(obj instanceof Array) {
      return obj.reduce((arr, item, i) => {
          arr[i] = deepCopy(item);
          return arr;
      }, []);
  }

  if(obj instanceof Object) {
      return Object.keys(obj).reduce((newObj: any, key: string) => {  // eslint-disable-line
          newObj[key] = deepCopy((obj as any)[key]);  // eslint-disable-line
          return newObj;
      }, {});
  }
}