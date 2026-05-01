import os
import glob

# Path to static directory
static_dir = r'c:\Users\ruwan\OneDrive\Desktop\supermarket_system\src\main\resources\static'
html_files = glob.glob(os.path.join(static_dir, '*.html'))

for file in html_files:
    with open(file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Simple replaces
    new_content = content.replace('Price ($)', 'Price (LKR)')
    new_content = new_content.replace('Amount ($)', 'Amount (LKR)')
    new_content = new_content.replace('Total Revenue ($)', 'Total Revenue (LKR)')
    new_content = new_content.replace('Total ($)', 'Total (LKR)')
    new_content = new_content.replace('-$', '-LKR ')
    new_content = new_content.replace('+$', '+LKR ')
    new_content = new_content.replace('−$', '−LKR ') # minus sign
    new_content = new_content.replace('=$', '=LKR ')
    
    # regex for JS template literals: $${something} -> LKR ${something}
    new_content = new_content.replace('$${', 'LKR ${')
    new_content = new_content.replace('"$"', '"LKR "')
    new_content = new_content.replace("'$'", "'LKR '")
    
    # HTML element contents like >$<
    new_content = new_content.replace('>$<', '>LKR <')
    new_content = new_content.replace('>$0.00<', '>LKR 0.00<')
    new_content = new_content.replace(' = $${', ' = LKR ${')

    if content != new_content:
        with open(file, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print('Updated HTML', file)

# Update SalesReport.java
java_file = r'c:\Users\ruwan\OneDrive\Desktop\supermarket_system\src\main\java\com\supermarket\inventory\entity\SalesReport.java'
with open(java_file, 'r', encoding='utf-8') as f:
    content = f.read()
new_content = content.replace('$', 'LKR ')
if content != new_content:
    with open(java_file, 'w', encoding='utf-8') as f:
        f.write(new_content)
    print('Updated Java', java_file)
