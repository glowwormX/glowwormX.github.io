```
void dfs(int step, int other)
{
        if 判断边界
        {
            相应操作
        }
        尝试每一种可能
        {
               满足check条件
               标记cache.add...
               继续下一步dfs(step+1. other+1)
               恢复初始状态（回溯的时候要用到）cache.delete...
        }
} 
```
